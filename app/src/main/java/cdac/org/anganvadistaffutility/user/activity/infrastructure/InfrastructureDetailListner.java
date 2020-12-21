package cdac.org.anganvadistaffutility.user.activity.infrastructure;

import java.util.ArrayList;

public interface InfrastructureDetailListner {
    void onDataChanged(ArrayList<String> ids, ArrayList<String> quantity);
}
