package rnd.plani.co.kr.dadfarm.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Created by RND on 2016-09-12.
 */
public class ProductData implements Serializable {
    public long id;
    public String title;
    public String sellerName;
    public String description;
    public String address;
    public String name;
    public long seller_id;
    public String price;
    public String bank_name;
    public String bank_account;
    public String bank_account_holder;
    public int order_count;
    public int review_count;
    public String created_time;
    public String updated_time;
    public List<String> images;
    public PersonalData friend;
    public PersonalData manager;
    public PersonalData seller;
    public String share_link;
    public HashMap<String,String> relationships;
}
