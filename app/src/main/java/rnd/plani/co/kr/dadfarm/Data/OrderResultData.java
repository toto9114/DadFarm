package rnd.plani.co.kr.dadfarm.Data;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Created by RND on 2016-09-12.
 */
public class OrderResultData implements Serializable{
    public long id;
    public String order_number;
    public long product_id;
    public PersonalData seller;
    public PersonalData manager;
    public PersonalData friend;
    public PersonalData shopper;
    public String product_title;
    public String product_name;
    public int product_price;
    public int quantity;
    public String shipping_address;
    public String shipping_phone_number;
    public String shipping_recipient_name;
    public String bank_name;
    public String bank_account;
    public String bank_account_holder;
    public String created_time;
    public String updated_time;
    public List<String> images;
    public HashMap<String, String> relationships;
    public boolean reviewed;
}
