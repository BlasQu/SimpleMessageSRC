package com.example.simplemessage.feature.splash


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.simplemessage.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class EspressoTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SplashActivity::class.java)
    // Używanie Thread.sleep() jest mniej efektywniejsze od IdlingResources, ale szybsze do wykonania.
    // Test pomyślnie wykona się tylko raz (bez restartu), ponieważ zmieniamy dynamicznie tekst,
    // dlatego w teście wchodzi do tekstu bez jego zmiany.
    @Test
    fun espressoTest() {
        val recyclerView = onView(
            allOf(
                withId(R.id.rv_messages),
                childAtPosition(
                    withClassName(`is`("android.widget.FrameLayout")),
                    0
                )
            )
        )
        Thread.sleep(7000)
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val materialButton = onView(
            allOf(
                withId(R.id.btn_edit), withText("Edytuj"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment_container),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val appCompatEditText = onView(
            allOf(
                withId(R.id.input_title),
                withText("Donald Tusk ostro o opozycji: Dla bojących się nie ma litości"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment_container),
                        0
                    ),
                    7
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("Donald Tusk ostro o opozycji: Dla bojących się nie ma litości"))

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.input_title),
                withText("Donald Tusk ostro o opozycji: Dla bojących się nie ma litości"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment_container),
                        0
                    ),
                    7
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(closeSoftKeyboard())

        val materialButton2 = onView(
            allOf(
                withId(R.id.btn_edit), withText("Zaktualizuj"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment_container),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())

        val appCompatImageButton = onView(
            allOf(
                withContentDescription("Navigate up"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            withId(R.id.holder_toolbar),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val appCompatImageView = onView(
            allOf(
                withId(R.id.img_add_post),
                childAtPosition(
                    allOf(
                        withId(R.id.layout_add_item),
                        childAtPosition(
                            withId(R.id.holder_toolbar),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatImageView.perform(click())

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.text_add_title),
                childAtPosition(
                    allOf(
                        withId(R.id.layout_add_item),
                        childAtPosition(
                            withId(R.id.holder_toolbar),
                            1
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText3.perform(replaceText("tak"), closeSoftKeyboard())

        val appCompatImageView2 = onView(
            allOf(
                withId(R.id.img_add_post),
                childAtPosition(
                    allOf(
                        withId(R.id.layout_add_item),
                        childAtPosition(
                            withId(R.id.holder_toolbar),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatImageView2.perform(click())

        val appCompatImageButton2 = onView(
            allOf(
                withContentDescription("Navigate up"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            withId(R.id.holder_toolbar),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageButton2.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
