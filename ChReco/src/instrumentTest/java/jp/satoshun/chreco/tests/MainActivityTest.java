package jp.satoshun.chreco.tests;


import android.test.ActivityInstrumentationTestCase2;
import jp.satoshun.chreco.MainActivity;

import java.lang.reflect.Field;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity mcontext;

    public MainActivityTest(Class<MainActivity> activityClass) {
        super(activityClass);
    }

    public void setUp() {
        mcontext = getActivity();
    }

    public void tearDown() {
        mcontext.finish();
    }

    public void testLoop() throws Exception {
        for (int i = 0; i < 100; i++) {
            mcontext.finish();
            mcontext = getActivity();
        }
    }

    public void testFeedUrlList() throws Exception {
        Class c = mcontext.getClass();
        Field field = c.getDeclaredField("feedUrlList");
        field.setAccessible(true);

        String[] feedUrlList = (String[]) field.get(mcontext);
        assertNotNull(feedUrlList);
        assertTrue(feedUrlList.length >= 1);
    }
}
