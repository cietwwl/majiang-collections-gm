package com.randioo.majiang_collections_client.component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.randioo.majiang_collections_client.UIComponent;
import com.randioo.randioo_server_base.annotation.PTAnnotation;
import com.randioo.randioo_server_base.navigation.Navigation;
import com.randioo.randioo_server_base.template.IActionSupport;
import com.randioo.randioo_server_base.utils.PackageUtil;
import com.randioo.randioo_server_base.utils.SpringContext;
import com.randioo.randioo_server_base.utils.StringUtils;

@Component
public class Navigater implements UIComponent {
    public void init() {
        Field field = ReflectionUtils.findField(Navigation.class, "navigate");
        ReflectionUtils.makeAccessible(field);
        @SuppressWarnings("unchecked")
        Map<String, IActionSupport> map = (Map<String, IActionSupport>) ReflectionUtils.getField(field, null);

        List<Class<?>> classes = PackageUtil.getClasses("com.randioo.majiang_collections_client.action");
        for (Class<?> clazz : classes) {
            String name = clazz.getSimpleName();

            if (StringUtils.isNullOrEmpty(name)) {
                continue;
            }

            String beanName = StringUtils.firstStrToLowerCase(name);

            IActionSupport entity = SpringContext.getBean(beanName);
            PTAnnotation annotion = entity.getClass().getAnnotation(PTAnnotation.class);
            Class<?> sc = annotion.value();
            String scName = sc.getSimpleName();
            map.put(scName, entity);
        }
        field.setAccessible(false);
    }

}
