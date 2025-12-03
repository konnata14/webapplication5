import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Слушатель контекста для подсчета посещений.
 * Работает с Servlet API (javax.servlet.*)
 */
@WebListener
public class VisitCounterListener implements ServletContextListener {

    // Файл для хранения счетчика
    private static final String COUNTER_FILE = "/WEB-INF/counter.txt";

    // Потокобезопасный счетчик
    private AtomicLong counter = new AtomicLong(0);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        String realPath = ctx.getRealPath(COUNTER_FILE);

        if (realPath != null) {
            File f = new File(realPath);

            if (f.exists()) {
                // Читаем значение счетчика из файла
                try (BufferedReader r = new BufferedReader(
                        new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8))) {
                    String s = r.readLine();
                    if (s != null) counter.set(Long.parseLong(s.trim()));
                } catch (IOException ignored) {
                }
            } else {
                // Если файла нет, создаем пустой
                try {
                    f.getParentFile().mkdirs();
                    f.createNewFile();
                } catch (IOException ignored) {
                }
            }
        }

        // Сохраняем счетчик в контекст сервлета
        ctx.setAttribute("visitCounter", counter);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        String realPath = ctx.getRealPath(COUNTER_FILE);

        if (realPath != null) {
            File f = new File(realPath);
            try (Writer w = new OutputStreamWriter(new FileOutputStream(f, false), StandardCharsets.UTF_8)) {
                w.write(Long.toString(counter.get()));
            } catch (IOException ignored) {
            }
        }
    }
}
