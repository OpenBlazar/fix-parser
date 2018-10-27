package pl.zankowski.fixparser.web.bean.admin;

import com.google.inject.Inject;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.zankowski.fixparser.tracker.spi.TrackerService;
import pl.zankowski.fixparser.web.bean.AbstractBean;
import pl.zankowski.fixparser.web.util.FacesUtils;
import pl.zankowski.fixparser.web.util.ShiroUtils;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.management.relation.Role;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@ManagedBean(name = "adminBean")
@ViewScoped
public class AdminBean extends AbstractBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminBean.class);
    private static final DateTimeFormatter DATE_CHART_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private UserService userService;
    private TrackerService trackerService;
    private ShiroUtils shiroUtils;
    private FacesUtils facesUtils;

    private Set<Map.Entry<LocalDate, Integer>> trackerDailyData = new HashSet<>();
    private LineChartModel trackerChartModel = new LineChartModel();
    private List<TrackerData> trackerData = new ArrayList<>();
    private List<UserDetails> userDetails = new ArrayList<>();

    @PostConstruct
    @Override
    public void init() {
        super.init();
        try {
            if (shiroUtils.hasRole(Role.ADMIN_ROLE.getName())) {
                userDetails = userService.getUsers();

                trackerData = trackerService.getTrackerData().stream()
                        .sorted(Comparator.comparing(TrackerData::getParseDate))
                        .collect(Collectors.toList());

                // TODO do collector
                trackerDailyData = trackerService.getTrackerDailyDataAgg().entrySet().stream()
                        .collect(Collectors.toCollection(
                                () -> new TreeSet<>(Comparator.comparing(Map.Entry::getKey))));

                LineChartSeries chartSeries = new LineChartSeries();
                chartSeries.setLabel("Message number");
                trackerDailyData.forEach(data -> chartSeries.set(DATE_CHART_FORMATTER.format(data.getKey()), data.getValue()));

                trackerChartModel.addSeries(chartSeries);
                trackerChartModel.setTitle("Tracker Data");
                trackerChartModel.setLegendPosition("e");
                trackerChartModel.setShowPointLabels(true);
                trackerChartModel.setZoom(true);

                DateAxis dateAxis = new DateAxis("Dates");
                dateAxis.setTickFormat("%b %#d, %y");
                trackerChartModel.getAxes().put(AxisType.X, dateAxis);

                Axis yAxis = trackerChartModel.getAxis(AxisType.Y);
                yAxis.setLabel("Number");
                yAxis.setMin(0);
            } else {
                // TODO FIXME move to shiro rules
                facesUtils.redirect(BlazarURL.PARSER_URL);
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

    @Inject
    public void setShiroUtils(ShiroUtils shiroUtils) {
        this.shiroUtils = shiroUtils;
    }

    @Inject
    public void setFacesUtils(FacesUtils facesUtils) {
        this.facesUtils = facesUtils;
    }

    public List<UserDetails> getUserDetails() {
        return userDetails;
    }

    public List<TrackerData> getTrackerData() {
        return trackerData;
    }

    public LineChartModel getChartSeries() {
        return trackerChartModel;
    }

}
