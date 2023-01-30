package ru.levelp.at.levelp.at.homework2;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;


@Suite
@SelectPackages("ru.levelp.at.homework2")
@IncludeTags({"Positive", "Negative"})

public class AllTestsSuite {

}
