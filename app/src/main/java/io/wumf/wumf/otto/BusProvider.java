package io.wumf.wumf.otto;

import com.squareup.otto.Bus;

/**
 * Created by max on 21.04.16.
 */
public class BusProvider {

    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
        // No instances.
    }

}
