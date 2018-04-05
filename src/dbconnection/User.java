/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbconnection;

/**
 *
 * @author Whichhunter
 */
public class User {
    private String username;
    private String password;
    private String email;
    private String aboutthisuser;
    public User(String username,String password,String email,String aboutthisuser){
        this.username = username;
        this.password = password;
        this.email = email;
        this.aboutthisuser = aboutthisuser;
    }
    public void set_username(String username){
    this.username = username;
    }
    public String get_username(){
    return this.username;
    }
    public void set_password(String password){
        this.password = password;
    }
    public String get_password(){
        return this.password;
    }
    public void set_email(String email){
        this.email = email;
    }
    public String get_email(){
        return this.email;
    }
    public void set_abouthisuser(String aboutthisuser){
        this.aboutthisuser = aboutthisuser;
    }
    public String get_aboutthisuser(){
        return this.aboutthisuser;
    }
    
}