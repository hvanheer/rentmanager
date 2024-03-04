package com.epf.rentmanager.servlet;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
 @WebServlet("/cars")
    public class VehicleListServlet extends HttpServlet {
        private static final long serialVersionUID = 1L;
        private VehicleService vehicleService;

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
            ClientService clientService = context.getBean(ClientService.class);
            VehicleService vehicleService = context.getBean(VehicleService.class);
            List<Vehicule> vehicleList;
            try {
                vehicleList = vehicleService.findAll();
            } catch (ServiceException e) {
                throw new RuntimeException(e);
            }
            request.setAttribute("vehicles", vehicleList);

            this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/list.jsp").forward(request, response);
        }
}
