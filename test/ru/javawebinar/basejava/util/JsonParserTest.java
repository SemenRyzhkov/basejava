package ru.javawebinar.basejava.util;

import org.junit.Assert;
import org.junit.Test;
import ru.javawebinar.basejava.model.AbstractSection;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.TextSection;


public class JsonParserTest {
    @Test
    public void resumeTest() {
        Resume expected = ResumeTestData.resumeTest("uuid1", "fullName1");
        String json = JsonParser.writeContent(expected);
        System.out.println(json);
        Resume actual = JsonParser.readContent(json, Resume.class);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void sectionTest(){
        AbstractSection expectSection = new TextSection("Objective");
        String json = JsonParser.writeContent(expectSection, AbstractSection.class);
        System.out.println(json);
        AbstractSection actualSection = JsonParser.readContent(json, AbstractSection.class);
        Assert.assertEquals(expectSection, actualSection);

    }
}