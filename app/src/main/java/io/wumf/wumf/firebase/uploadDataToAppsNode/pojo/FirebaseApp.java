package io.wumf.wumf.firebase.uploadDataToAppsNode.pojo;

import java.util.List;

/**
 * Created by max on 28.06.16.
 */
public class FirebaseApp {

    private String name;
    private String icon;
    private List<String> phones;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }
}
