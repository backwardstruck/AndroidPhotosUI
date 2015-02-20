package com.rooney.poc.photos.test;

import android.os.Bundle;
import android.test.InstrumentationTestCase;

import com.rooney.poc.photos.fragments.ItemDetailFragment;

/**
 * Class to test
 */
public class ItemDetailTest extends InstrumentationTestCase {
    public void test() throws Exception {
        final int expected = 10;

        ItemDetailFragment fragment = ItemDetailFragment.newInstance(expected);

        Bundle bundle = fragment.getArguments();

        //null check
        assertNotNull(bundle);

        int reality = bundle.getInt("index");

        assertEquals(expected, reality);
    }

}
