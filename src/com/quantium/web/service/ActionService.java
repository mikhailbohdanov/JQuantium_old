package com.quantium.web.service;

import com.quantium.web.bean.core.Action;
import com.quantium.web.bean.core.StringTemplate;
import com.quantium.web.dao.UtilityDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by FREEMAN on 09.02.15.
 */
@Service
public class ActionService {

    public static void call(String parentName, Object... items) {
        ArrayList<Action> actions = coreService.getActions(parentName);

        Object _item = null;
        String actionClassName;
        for (Action action : actions) {
            _item           = null;
            actionClassName = action.getObjectClass();

            for (Object item : items)
                if (item.getClass().getName().equals(actionClassName))
                    _item = item;

            callAction(action, _item);
        }
    }

    public static boolean callAction(Action action, Object item) {
        switch (action.getType()) {
            case CALL_SQL:
                StringTemplate sql = new StringTemplate(action.getAction());

                try {
                    utilityDAO.execSQL(
                            sql.toStringObject(item)
                    );

                    return true;
                } catch (Exception e) {}
                break;
        }

        return false;
    }

    private static CoreService coreService;
    private static UtilityDAO utilityDAO;
    @Autowired
    public void init(CoreService coreService, UtilityDAO utilityDAO) {
        ActionService.coreService = coreService;
        ActionService.utilityDAO = utilityDAO;
    }


}
