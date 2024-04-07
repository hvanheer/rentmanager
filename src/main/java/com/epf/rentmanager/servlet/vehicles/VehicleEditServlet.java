package com.epf.rentmanager.servlet.vehicles;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cars/edit")
public class VehicleEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    private VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("vehicule", vehicleService.findById(Integer.parseInt(request.getParameter("id"))));
        } catch (ServiceException e) {
            throw new ServletException();
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/edit.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String manufacturer = request.getParameter("manufacturer");
            String modele = request.getParameter("modele");
            int seats = Integer.parseInt(request.getParameter("seats"));

            vehicleService.update(new Vehicule(
                    manufacturer,
                    0,
                    seats,
                    modele
            ));
        } catch (ServiceException e) {
            throw new ServletException();
        }
        response.sendRedirect(request.getContextPath() + "/cars");
    }
}