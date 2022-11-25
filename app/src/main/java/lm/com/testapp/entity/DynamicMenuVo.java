package lm.com.testapp.entity;

/**
 * 动态菜单实体
 *
 * @author tongxu_li
 *         Copyright (c) 2014 Shanghai P&C Information Technology Co., Ltd.
 * @since 2015-04-22 引入Gson注解，规范代码格式 modify by dlzhang
 */
public class DynamicMenuVo{

    private String id;
    private String sex;
    private String age;
    private String weight;
    private String height;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "DynamicMenuVo{" +
                "id='" + id + '\'' +
                ", sex='" + sex + '\'' +
                ", age='" + age + '\'' +
                ", weight='" + weight + '\'' +
                ", height='" + height + '\'' +
                '}';
    }
}
