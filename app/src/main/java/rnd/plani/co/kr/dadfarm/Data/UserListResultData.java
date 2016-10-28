package rnd.plani.co.kr.dadfarm.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by toto9114 on 2016-10-24.
 */

public class UserListResultData implements Serializable {
    public int count;
    public String next;
    public String previous;
    public List<PersonalData> results;
}
