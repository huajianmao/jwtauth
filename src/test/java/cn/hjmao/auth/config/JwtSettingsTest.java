package cn.hjmao.auth.config;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.Test;

class JwtSettingsTest {

  @Test
  void newJwtSettingsShouldFail() {
    try {
      Constructor<JwtSettings> constructor = JwtSettings.class.getDeclaredConstructor();
      assertTrue(Modifier.isPrivate(constructor.getModifiers()));
      constructor.setAccessible(true);
      constructor.newInstance();
    } catch (NoSuchMethodException e) {
      assert false;
    } catch (IllegalAccessException e) {
      assert false;
    } catch (InstantiationException e) {
      assert false;
    } catch (InvocationTargetException e) {
      assert true;
    }
  }
}
