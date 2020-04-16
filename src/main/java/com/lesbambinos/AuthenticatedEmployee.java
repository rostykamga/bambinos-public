package com.lesbambinos;


import com.lesbambinos.entity.Employee;
import java.util.Objects;

/**
 *
 * @author Rostand
 */
public class AuthenticatedEmployee {
    
    static final int PERMISSION_UPDATE_INTERVAL = 1500;
    private Employee employee;
    private static long permissionCheckTimestamp=0L;

    public AuthenticatedEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployeeEntity() {
        return employee;
    }
    
    public void logout(){
        employee = null;
    }
    
    public boolean isAdmin(){
        return employee.getUserName().equals("admin");
    }
    
    public static boolean isAdmin(Employee emp){
        return emp.getUserName().equals("admin");
    }
    
    public  boolean isSameEmployeeAs(Employee emp){
        return Objects.equals(emp.getId(), employee.getId());
    }
    
//    public boolean hasPermission(String permission) throws NoSuchFieldException {
//        
//        if(Permissions.AdminRigth.equals(permission)){
//           return isAdmin();
//        }
//        
//        // if the time ellapsed since the last check is more than a predefined time, then update employee to see if
//        // its rights have changed.
//        long currentCheckTimestamp = System.currentTimeMillis();
//        if(currentCheckTimestamp - permissionCheckTimestamp > PERMISSION_UPDATE_INTERVAL)
//            employee = (Employee)(new EmployeeModel().getById(employee.getId()));
//        
//        try {
//            Field field =  Employee.class.getDeclaredField(permission);
//            field.setAccessible(true);
//            int value = field.getInt(employee);
//            return value == 1;
//        } catch (NoSuchFieldException ex) {
//            throw ex;
//        } catch (SecurityException | IllegalArgumentException | IllegalAccessException ex) {
//            Logger.getLogger(AuthenticatedEmployee.class.getName()).log(Level.SEVERE, null, ex);
//            return false;
//        } 
//    }
    
}
