package com.blazarquant.bfp.web.bean.admin;

import com.blazarquant.bfp.core.security.enums.UserRole;
import com.blazarquant.bfp.data.tracker.TrackerData;
import com.blazarquant.bfp.data.user.UserDetails;
import com.blazarquant.bfp.services.tracker.TrackerService;
import com.blazarquant.bfp.services.user.UserService;
import com.blazarquant.bfp.web.bean.AbstractBean;
import com.blazarquant.bfp.web.util.BlazarURL;
import com.google.inject.Inject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Wojciech Zankowski
 */
@ManagedBean(name = "adminBean")
@ViewScoped
public class AdminBean extends AbstractBean {

    private final static Logger LOGGER = LoggerFactory.getLogger(AdminBean.class);

    private UserService userService;
    private TrackerService trackerService;

    private Set<Map.Entry<LocalDate, Integer>> trackerDailyData = new HashSet<>();
    private LineChartModel trackerChartModel = new LineChartModel();
    private List<TrackerData> trackerData = new ArrayList<>();
    private List<UserDetails> userDetails = new ArrayList<>();

    @PostConstruct
    @Override
    public void init() {
        super.init();
        try {
            Subject subject = SecurityUtils.getSubject();
            if (subject.hasRole(UserRole.ADMIN.getRole())) {
                userDetails = userService.getUsers();

                trackerData = trackerService.getTrackerData().stream()
                        .sorted(Comparator.comparing(TrackerData::getParseDate))
                        .collect(Collectors.toList());

                // TODO do collector
                trackerDailyData = trackerService.getTrackerDailyDataAgg().entrySet().stream()
                        .collect(Collectors.toCollection(
                                () -> new TreeSet<>(Comparator.comparing(Map.Entry::getKey))));

                ChartSeries chartSeries = new ChartSeries();
                chartSeries.setLabel("Message number");
                trackerDailyData.forEach(data -> chartSeries.set(data.getKey().toString(), data.getValue()));

                trackerChartModel.addSeries(chartSeries);
                trackerChartModel.setTitle("Tracker Data");
                trackerChartModel.setLegendPosition("e");
                trackerChartModel.setShowPointLabels(true);
            } else {
                // TODO FIXME move to shiro rules
                FacesContext.getCurrentInstance().getExternalContext().redirect(BlazarURL.PARSER_URL);
            }
        } catch (Exception e) {
            LOGGER.error("Failed to init admin page.", e);
        }
    }

    @Inject
    public void setTrackerService(TrackerService trackerService) {
        this.trackerService = trackerService;
    }

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public List<UserDetails> getUserDetails() {
        return userDetails;
    }

    public Set<Map.Entry<LocalDate, Integer>> getTrackerDailyData() {
        return trackerDailyData;
    }

    public List<TrackerData> getTrackerData() {
        return trackerData;
    }

    public LineChartModel getChartSeries() {
        return trackerChartModel;
    }

}
