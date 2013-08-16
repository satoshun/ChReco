package jp.satoshun.chreco.tests.libs;

import android.content.Context;
import android.test.AndroidTestCase;
import jp.satoshun.chreco.libs.User;

public class UserTest extends AndroidTestCase {
    Context context;

    public void setUp() {
        context = getContext();
    }

    public void testUserId() throws Exception {
        assertNotNull(User.getId(context));
    }
}
