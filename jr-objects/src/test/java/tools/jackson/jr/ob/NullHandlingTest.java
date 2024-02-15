package tools.jackson.jr.ob;

import java.util.LinkedHashMap;
import java.util.Map;

public class NullHandlingTest extends TestBase
{
    static class Bean107 {
        public byte[] b = new byte[0];
    }

    static class StringBean {
        public String str = "a";
    }

    // Test to verify that outputting of nulls is configurable
    public void testMapNullEntries() throws Exception
    {
        Map<String,Object> map = new LinkedHashMap<String,Object>();
        map.put("a", 1);
        map.put("b", null);
        // By default we do NOT write null-valued entries:
        assertEquals("{\"a\":1}", JSON.std.asString(map));
        // but we can disable it easily
        assertEquals("{\"a\":1,\"b\":null}",
                JSON.std.with(JSON.Feature.WRITE_NULL_PROPERTIES).asString(map));
    }

    public void testNullForString() throws Exception
    {
        assertNull(JSON.std.beanFrom(StringBean.class, "null"));

        StringBean bean = JSON.std.beanFrom(StringBean.class, a2q("{'str':null}"));
        assertNull(bean.str);
    }

    // [jackson-jr#107]: nulls should be accepted as byte[]
    public void testNullForByteArray() throws Exception
    {
        assertNull(JSON.std.beanFrom(Bean107.class, "null"));

        Bean107 bean = JSON.std.beanFrom(Bean107.class, a2q("{'b':null}"));
        assertNull(bean.b);
    }
}