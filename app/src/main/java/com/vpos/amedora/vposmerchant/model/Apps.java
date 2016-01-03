package com.vpos.amedora.vposmerchant.model;

/**
 * Created by Amedora on 7/16/2015.
 */
public class Apps {
    int id;
    String app_id;
    int status;
    String updated_at;
    String created_at;
    String bank_name;
    String sort_code;
    String acc_no;
    int route_id;
    String route_name;
    int terminal_id;

    public Apps(){}
    public Apps(String app_id, int status){
        this.app_id = app_id;
        this.status = status;
    }

    public Apps(String app_id, int status, String updated_at, String bank_name, String sort_code, String acc_no, String created_at, int terminal_id, int route_id, String route_name){
        this.app_id = app_id;
        this.status = status;
        this.bank_name = bank_name;
        this.sort_code = sort_code;
        this.acc_no = acc_no; //merchant id
        this.route_id = route_id;
        this.route_name = route_name;
        this.terminal_id = terminal_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId(){
        return this.id;
    }

    public String getApp_id(){
        return this.app_id;
    }

    public int getStatus(){
        return this.status;
    }

    public String getBank_name(){return this.bank_name;}

    public String getSort_code(){ return this.sort_code;}

    public String getAcc_no(){return  this.acc_no;}

    public String getUpdated_at(){
        return this.updated_at;
    }

    public String getCreated_at(){
        return this.created_at;
    }

    public int getRoute_id() {
        return route_id;
    }

    public int getTerminal_id() {
        return terminal_id;
    }

    public String getRoute_name() {
        return route_name;
    }



    public void setId(int id){
        this.id = id;
    }

    public void setApp_id(String app_id){
        this.app_id = app_id;
    }

    public void setStatus(int status){
        this.status = status;
    }

    public void setUpdated_at(String updated_at){
        this.updated_at = updated_at;
    }

    public void setCreated_at(String created_at){
        this.created_at = created_at;
    }

    public void setBank_name(String bank_name){ this.bank_name = bank_name; }

    public void setSort_code(String sort_code){ this.sort_code = sort_code; }

    public void setAcc_no(String acc_no){ this.acc_no = acc_no; }

    public void setRoute_id(int route_id) {
        this.route_id = route_id;
    }

    public void setRoute_name(String route_name) {
        this.route_name = route_name;
    }

    public void setTerminal_id(int terminal_id) {
        this.terminal_id = terminal_id;
    }
}
