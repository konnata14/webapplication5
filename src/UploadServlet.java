import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@WebServlet("/upload")
@MultipartConfig(fileSizeThreshold = 1024*1024, maxFileSize = 5L*1024*1024, maxRequestSize = 6L*1024*1024)
public class UploadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession(false);
        User user = s == null ? null : (User) s.getAttribute("user");
        if (user == null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        Part filePart = req.getPart("image");
        if (filePart == null || filePart.getSize() == 0) {
            req.setAttribute("msg", "Файл не выбран");
            req.getRequestDispatcher("/profile.jsp").forward(req, resp);
            return;
        }

        String uploadsPath = getServletContext().getRealPath("/uploads");
        File uploadsDir = new File(uploadsPath);
        if (!uploadsDir.exists()) uploadsDir.mkdirs();

        String submitted = filePart.getSubmittedFileName();
        String safeName = System.currentTimeMillis() + "_" + submitted.replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");
        File target = new File(uploadsDir, safeName);
        try (InputStream in = filePart.getInputStream()) {
            Files.copy(in, target.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        s.setAttribute("uploadedImage", "uploads/" + safeName);

        req.setAttribute("msg", "Файл загружен");
        req.getRequestDispatcher("/profile.jsp").forward(req, resp);
    }
}
