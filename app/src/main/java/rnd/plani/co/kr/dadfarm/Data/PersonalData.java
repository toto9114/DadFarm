package rnd.plani.co.kr.dadfarm.Data;

import java.io.Serializable;

/**
 * Created by RND on 2016-09-12.
 */
public class PersonalData implements Relationships,Serializable {
    public long id;
    public String first_name;
    public String last_name;
    public ProfileData profile;
}
