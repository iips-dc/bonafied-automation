
package bonafide;

import java.sql.*;

/**
 *
 * @author ishant0
 */
public class CreateCertificate {
    public void create(SecondClass sc, Object ob){
      //Step-1 Fetching data which is last entered in firstInfo table. Already know due to sc
      //Step-2  Fetching formate from type table.
   try{
       c = new Connect();
       con = c.getConnection();
       ps = con.prepareStatement("select formate from type where typeName = '"+sc.getCert_type()+"'");
       rs = ps.executeQuery();       
       if(rs.next()){
          formate = rs.getString("formate") ;
       }
       else{
           javax.swing.JOptionPane.showMessageDialog(null, "Internal error formate type or Certificate type not found.");
       }     
       c.closeConnection(con, ps, rs);
       System.out.println("Step 2 completed!!! of create class/mtd");
   }
   catch(Exception e){
       javax.swing.JOptionPane.showMessageDialog(null, "Problem in fetching formate"+e); 
       //System.exit(1000);  not working!!
   }
   finally{c.closeConnection(con, ps, rs); }
   
   //Step-3 Merging data with formate
   try{
       c = new Connect();       
       to_deliver = c.splitJoin(formate, "{name}", sc.getName());
       to_deliver = c.splitJoin(to_deliver, "{roll}", sc.getRollnum());
       to_deliver = c.splitJoin(to_deliver, "{father}", sc.getFather_name());
       to_deliver = c.splitJoin(to_deliver, "{course}",  c.getFullFormOfcourse(sc.getCourse()));
       to_deliver = c.splitJoin(to_deliver, "{sem}",Integer.toString(sc.getSemester()));   
       //Since Enrollment NO. is optional checking if it is entered or not.
       if(sc.getEnrollment().trim().length() == 0){
           int index = to_deliver.indexOf(" & Enrollment");
           to_deliver = to_deliver.substring(0, index) + to_deliver.substring(index+29);
       }
       else{
       to_deliver = c.splitJoin(to_deliver, "{enrollment}", sc.getEnrollment());
       }                       
       to_deliver = c.splitJoin(to_deliver, "{yr}", c.getYearsofCourse(sc.getCourse(), "Problem in fetching period of course from database. "));
       System.out.println("Inside step 3.");
       if(ob != null){            
            AllRequiredClass obj = (AllRequiredClass)ob; 
            if(sc.getRequire_cgpa().equals("y")){ 
                System.out.println("Kya bhai m in");               
                to_deliver = c.splitJoin(to_deliver, "{cgpa}",Float.toString(obj.getCgpa()));   
                //Since SGPA is optional
                if(obj.getSgpa() == 0.0){                     
                    int index = to_deliver.indexOf(" and SGPA");                   
                    to_deliver = to_deliver.substring(0, index) + to_deliver.substring(index+19);
                }
                else{
                    to_deliver = c.splitJoin(to_deliver, "{sgpa}", Float.toString(obj.getSgpa()));
                }
                to_deliver = c.splitJoin(to_deliver, "{in sem}", Integer.toString(obj.getIn_sem()));
                to_deliver = c.splitJoin(to_deliver, "{pursuing sem}", Integer.toString(obj.getPursuing_sem()));              
            }
            if(sc.getRequire_address().equals("y")) {
                to_deliver = c.splitJoin(to_deliver, "{local address}", obj.getLocal_address());
                to_deliver = c.splitJoin(to_deliver, "{permanent address}", obj.getPermanent_address()); 
            }            
            if(sc.getRequire_year().equals("y")){
                //In many Certificate types Only completion year is used!.
                System.out.println("Addmission year"+obj.getAdmission_year());
                if(obj.getAdmission_year() == 0){
                    //when addmission year us mentioned in formate:
                    int index = to_deliver.indexOf("starting from year");
                    if(index == -1){}
                    else{
                        to_deliver = to_deliver.substring(0, index) + "in year" + to_deliver.substring(index+38);
                    }
                }
                else{
                    to_deliver = c.splitJoin(to_deliver, "{admission year}", Integer.toString(obj.getAdmission_year()));
                }                 
                 to_deliver = c.splitJoin(to_deliver, "{completion year}", Integer.toString(obj.getCompletion_year()));  
                 to_deliver = c.splitJoin(to_deliver, "{pursuing sem}", Integer.toString(obj.getPursuing_sem())); 
            }
            System.out.println("sc.getOther_requirement1() is "+sc.getOther_requirement1());
            if(!sc.getOther_requirement1().equals("n")){
                System.out.println("to replace"+obj.getTo_replace1()+" by replace"+obj.getBy_replace());
                to_deliver = c.splitJoin(to_deliver, sc.getOther_requirement1(), obj.getBy_replace());
            }
            if(!sc.getOther_requirement2().equals("n")){               
                to_deliver = c.splitJoin(to_deliver, sc.getOther_requirement2(), obj.getBy_replace());
            }
            if(!sc.getOther_requirement3().equals("n")){                
                to_deliver = c.splitJoin(to_deliver, sc.getOther_requirement3(), obj.getBy_replace());
            }
            /*
            else{                         
                to_deliver = c.splitJoin(to_deliver, "{birth date}", obj.getBirth_date());
                to_deliver = c.splitJoin(to_deliver, "{birth place}", obj.getBirth_place());   
            }
             * 
             */
       c.closeConnection(con, ps, rs);      
       System.out.println("Step 3 completed of create mtd!!!");
       System.out.println(to_deliver);       
       }
   }catch(Exception e){javax.swing.JOptionPane.showMessageDialog(null, "Problem while merging!"+e);}
   finally{c.closeConnection(con, ps, rs); }
   
   //Step-4 Sending this above result op to toDeliver table:
   
   try{     
       c = new Connect();
        con = c.getConnection();
        ps =con.prepareStatement("insert into toDeliver (op, course, sem) values( '"+to_deliver+"', '"+sc.getCourse()+"', "+sc.getSemester()+")");        
        ps.executeUpdate();
        c.closeConnection(con, ps, null);
   }catch(Exception e){javax.swing.JOptionPane.showMessageDialog(null, "Problem in delivering data!! "+e);}
   finally{c.closeConnection(con, ps, rs); }
        
    // Step-5 Now code for jasper reports:    
       try{ 
        Connection conn =  DriverManager.getConnection("jdbc:sqlite:bonafide.db");
        net.sf.jasperreports.engine.design.JasperDesign jasperDesign = net.sf.jasperreports.engine.xml.JRXmlLoader.load("GenerateReport.jrxml");
        //String sql = "select * from toDeliver, PI where course = course_name and toDeliver.sem <= PI.sem order by date DESC LIMIT 1"; 
      String sql = "select * from toDeliver order by date DESC LIMIT 1";
      net.sf.jasperreports.engine.design.JRDesignQuery newQuery = new net.sf.jasperreports.engine.design.JRDesignQuery();
      newQuery.setText(sql);
      jasperDesign.setQuery(newQuery);
      net.sf.jasperreports.engine.JasperReport jasperReport = net.sf.jasperreports.engine.JasperCompileManager.compileReport(jasperDesign);
      net.sf.jasperreports.engine.JasperPrint jasperPrint = net.sf.jasperreports.engine.JasperFillManager.fillReport(jasperReport, null, conn);
      net.sf.jasperreports.view.JasperViewer.viewReport(jasperPrint);  
      
      conn.close();     
    }
    catch (net.sf.jasperreports.engine.JRException e){e.printStackTrace();}
    catch (SQLException e){e.printStackTrace();}
    catch(Exception e){javax.swing.JOptionPane.showMessageDialog(null, "Problem in generating report! "+e);} 
       
   //sending issued certificate on web
       /*
  try{       
    c = new Connect();
    con = c.getConnection();
    ps = con.prepareStatement("select op from toDeliver, PI where course = course_name and toDeliver.sem <= PI.sem order by date DESC LIMIT 1");
    rs = ps.executeQuery();
    new EmailTest().sendMail(rs.getString("op")); 
    c.closeConnection(con, ps, rs); 
  }
  catch(Exception e){javax.swing.JOptionPane.showMessageDialog(null, "Problem in generating report! "+e);} 
       
        * 
        */
  }
    
    
    //Variables declarations
    Connection con;
    PreparedStatement ps;
    Statement st;
    ResultSet rs;
    private Connect c;
    
    private String formate;
    private String to_deliver;
}
