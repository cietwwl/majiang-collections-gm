package com.randioo.majiang_collections_client.component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.util.ReflectionUtils;

import com.randioo.majiang_collections_client.UIComponent;
import com.randioo.randioo_server_base.navigation.Navigation;
import com.randioo.randioo_server_base.template.IActionSupport;
import com.randioo.randioo_server_base.utils.PackageUtil;

public class NavigationComponent implements UIComponent {
    public void init() {
        Field field = ReflectionUtils.findField(Navigation.class, "navigate");
        ReflectionUtils.makeAccessible(field);
        @SuppressWarnings("unchecked")
        Map<String, IActionSupport> map = (Map<String, IActionSupport>) ReflectionUtils.getField(field, null);
        
       List<Class<?>> classes = PackageUtil.getClasses("com.randioo.majiang_collections_client.action");
       
    }
}
