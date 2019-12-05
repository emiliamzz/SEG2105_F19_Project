package com.example.seg2105_f19_project;

import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class MoreTestCases {

    @Rule
    public ActivityTestRule<AddHours> addHoursActivityTestRule = new ActivityTestRule<AddHours>(AddHours.class);
    private AddHours addHours = null;
    private TextView textView;

    @Before
    public void setUp() throws Exception {
        addHours = addHoursActivityTestRule.getActivity();
    }

    @Test
    @UiThreadTest
    public void checkSundayOpen() throws Exception {
        assertNotNull(addHours.findViewById(R.id.sundayOpen));
        textView = addHours.findViewById(R.id.sundayText);
        textView.setText("asdfghjkl");
        String name = textView.getText().toString();
        assertNotEquals("Sunday: ", name);
    }

    @Test
    @UiThreadTest
    public void checkSundayClosed() throws Exception {
        assertNotNull(addHours.findViewById(R.id.sundayClose));
        textView = addHours.findViewById(R.id.sundayText);
        textView.setText("asdfghjkl");
        String name = textView.getText().toString();
        assertNotEquals("Sunday: ", name);
    }

}
