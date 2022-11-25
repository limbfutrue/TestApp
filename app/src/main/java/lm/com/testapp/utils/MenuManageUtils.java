package lm.com.testapp.utils;

import android.text.TextUtils;

import java.util.List;

import lm.com.testapp.entity.DynamicMenuVo;

/**
 * Created by PSBC-26 on 2021/7/20.
 */

public class MenuManageUtils {


    String testStr = "\"12\";\"fdsa\";\"asa\";\"ff\";\"gg\"\n" +
            "\"13\";\"13fdsa\";\"13asa\";\"13ff\";\"13gg\"\n" +
            "\"14\";\"14fdsa\";\"14asa\";\"14ff\";\"14gg\"\n" +
            "\"15\";\"15fdsa\";\"15asa\";\"15ff\";\"15gg\"\n" +
            "\"17\";\"17fdsa\";\"17asa\";\"17ff\";\"17gg\"";
    String testStr2 = "\"12\";\"11fdsa\";\"a11sa\";\"f11f\";\"g11g\"\n" +
            "\"15\";\"17fdsa\";\"17asa\";\"17ff\";\"17gg\"";

    String testStr3 = "\"16\";\"16fdsa\";\"a11sa\";\"f11f\";\"g11g\"\n" +
            "\"18\";\"18fdsa\";\"17asa\";\"17ff\";\"17gg\"";

    String testStr4 = "\"13\";\"13fdsa\";\"13asa\";\"13ff\";\"13gg\"\n" +
            "\"18\";\"18fdsa\";\"17asa\";\"17ff\";\"17gg\"";


    /**
     * 执行菜单更新操作
     */
    public static void exeUpdate(){
//        upDateMenu();
    }


    /**
     * @param deleteValue 删除的菜单数据
     * @param updateValue 修改的菜单数据
     * @param addValue    新增的菜单数据
     */
    public static void upDateMenu(List<DynamicMenuVo> result, String deleteValue, String updateValue, String addValue) {
        deleteMenu(result, deleteValue);

        //新增菜单
        if (!TextUtils.isEmpty(addValue)) {
            String[] aValue = addValue.split("\\n");
            String[] addSValue = null;
            for (int i = 0; i < aValue.length; i++) {
                //遍历菜单
                //获取单个菜单的值，并生成对象赋值
                addSValue = aValue[i].split(";");
                addMenuVo(result, -1, addSValue);
            }
        }

        if (TextUtils.isEmpty(updateValue)) {
            return;
        }

        boolean isFinish = false;
        String[] uValue = updateValue.split("\\n");
        String[] singleValue = null;

        //遍历菜单
        for (int i = 0; i < uValue.length; i++) {
            //获取单个菜单的值，并生成对象赋值
            singleValue = uValue[i].split(";");
            //修改类型添加判断
            for (int j = 0; j < result.size(); j++) {
                if (result.get(j).getId().equals(singleValue[0].replace("\"", ""))) {
                    //删除本地已经添加的，然后再新增
                    result.remove(j);
                    addMenuVo(result, -1, singleValue);
                    break;
                }
                //修改类菜单：代表没有重复的菜单id，需要先删除后新增
                if (isFinish) {
                    //删除基线里的然后新增
                    deleteMenuById(result, singleValue[0].replace("\"", ""));
                    addMenuVo(result, -1, singleValue);
                    isFinish = false;
                }
            }

        }
    }

    /**
     * 添加数据到列表中
     *
     * @param position 添加的位置
     * @param voValue  要添加的数据
     */
    private static void addMenuVo(List<DynamicMenuVo> result, int position, String[] voValue) {
        DynamicMenuVo vo = null;
        vo = new DynamicMenuVo();
        vo.setId(voValue[0].replace("\"", ""));
        vo.setAge(voValue[1].replace("\"", ""));
        vo.setSex(voValue[2].replace("\"", ""));
        vo.setHeight(voValue[3].replace("\"", ""));
        vo.setWeight(voValue[4].replace("\"", ""));
        if (position == -1) {
            result.add(vo);
        } else {
            result.add(position, vo);
        }
    }

    /**
     * 移除菜单
     *
     * @param value
     */
    private static void deleteMenu(List<DynamicMenuVo> result, String value) {
        if (TextUtils.isEmpty(value)) {
            return;
        }
        String[] deleteMenu = value.split("\\n");
        String[] menuInfo = null;
        for (int i = 0; i < deleteMenu.length; i++) {
            menuInfo = deleteMenu[i].split(";");
            for (int j = 0; j < result.size(); j++) {
                if (result.get(j).getId().equals(menuInfo[0].replace("\"", ""))) {
                    result.remove(j);
                    break;
                }
            }
        }

    }

    /**
     * 移除菜单
     *
     * @param menuId
     */
    private static void deleteMenuById(List<DynamicMenuVo> result, String menuId) {
        for (int j = 0; j < result.size(); j++) {
            if (result.get(j).getId().equals(menuId)) {
                result.remove(j);
                return;
            }
        }
    }
}
