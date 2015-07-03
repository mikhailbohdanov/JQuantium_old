package com.quantium.web.service.authentication;

import com.quantium.web.bean.core.Response;
import com.quantium.web.bean.security.UserSecurity;
import com.quantium.web.bean.social.UserProfile;
import com.quantium.web.controller.CONFIG;
import com.quantium.web.dao.UserIO;
import com.quantium.web.dao.UtilityDAO;
import com.quantium.web.dao.annotation.Table;
import com.quantium.web.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserIO {

	@Autowired
	private SecurityService securityService;

    @Autowired
    private UtilityDAO utilityDAO;

    public boolean createUser(UserSecurity userSecurity, Response response) {



        return false;
    }

    public int _getUserCount() {
        try {
            return utilityDAO.execSQL(
                    _USER_GET_COUNT,
                    Integer.class
            );
        } catch (Exception e) {
            return 0;
        }
    }

    public ArrayList<UserSecurity> _getUserList() {
        return _getUserList("userId", true, 0, CONFIG.PAGE_LIMIT);
    }
    public ArrayList<UserSecurity> _getUserList(String orderColumn, boolean desc, int offset, int limit) {
        Class[] _classes = {UserSecurity.class, UserProfile.class};

        if (orderColumn == null)
            return _getUserList();

        if (offset < 0)
            offset = 0;

        if (limit == 0)
            limit = CONFIG.PAGE_LIMIT;

        String _orderColumn = null, tableName = null;
        for (Class _class : _classes)
            try {
                _class.getDeclaredField(orderColumn);
                _orderColumn = orderColumn;
                tableName = ((Table) _class.getDeclaredAnnotation(Table.class)).value();
                break;
            } catch (Exception e) {
                continue;
            }

        try {
            return (ArrayList<UserSecurity>) utilityDAO.getRowList(
                    _USER_GET_LIST_WITH_ORDER.toString(tableName, _orderColumn, (desc ? "DESC" : "ASC")),
                    (new MapSqlParameterSource())
                            .addValue("offset", offset)
                            .addValue("limit", limit),
                    USER
            );
        } catch (Exception e) {
            return null;
        }
    }


}
