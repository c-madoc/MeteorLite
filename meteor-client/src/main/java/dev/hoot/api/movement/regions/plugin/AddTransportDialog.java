package dev.hoot.api.movement.regions.plugin;

import dev.hoot.api.entities.TileObjects;
import dev.hoot.api.movement.regions.RegionManager;
import dev.hoot.api.scene.Tiles;
import meteor.eventbus.Subscribe;
import net.miginfocom.swing.MigLayout;
import net.runelite.api.Tile;
import net.runelite.api.TileObject;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.MenuOptionClicked;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.sponge.util.Logger;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class AddTransportDialog extends JFrame {
    private static final ScheduledExecutorService EXECUTOR = Executors.newSingleThreadScheduledExecutor();
    private static final Logger logger = new Logger("AddTransportDialog");

    private final JTextField fromText;

    private final JTextField toText;

    private final JTextField actionText;
    private final JTextField objNameText;
    private final JTextField objIdText;

    private final DefaultListModel<TransportLink> listModel;
    private final JList<TransportLink> transportLinks;

    private final OkHttpClient okHttpClient;

    public AddTransportDialog(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;

        setLayout(new GridLayout(1, 2));
        setMinimumSize(new Dimension(550, 350));

        listModel = new DefaultListModel<>();

        JPanel configPanel = new JPanel(new MigLayout("fill"));
        configPanel.setBorder(new TitledBorder("Config"));
        configPanel.setMinimumSize(new Dimension(250, 350));

        JPanel listPanel = new JPanel(new MigLayout());
        listPanel.setBorder(new TitledBorder("Links"));
        listPanel.setMinimumSize(new Dimension(400, 350));

        add(configPanel);
        add(listPanel);

        configPanel.add(new JLabel("Source tile: "));
        configPanel.add(fromText = new JTextField(), "growx");
        JButton fromButton = new JButton("...");
        configPanel.add(fromButton, "wrap");

        configPanel.add(new JLabel("Destination tile: "));
        configPanel.add(toText = new JTextField(), "growx");
        JButton toButton = new JButton("...");
        configPanel.add(toButton, "wrap");

        JButton selectObject = new JButton("Select object");
        configPanel.add(selectObject, "spanx 2, wrap");
        configPanel.add(new JLabel("Action: "));
        configPanel.add(actionText = new JTextField(), "growx, wrap");

        configPanel.add(new JLabel("Object name: "));
        configPanel.add(objNameText = new JTextField(), "growx, wrap");

        configPanel.add(new JLabel("Object id: "));
        configPanel.add(objIdText = new JTextField(), "growx, wrap");

        JButton addLink = new JButton("Add link");
        configPanel.add(addLink, "growx, skip");

        JScrollPane listScroll = new JScrollPane(transportLinks = new JList<>(listModel));

        listPanel.add(listScroll, "grow, push, wrap");
        JButton submit = new JButton("Submit");
        listPanel.add(submit);

        fromButton.addActionListener(a -> RegionPlugin.selectingSourceTile = true);
        toButton.addActionListener(a -> RegionPlugin.selectingDestinationTile = true);
        selectObject.addActionListener(a -> RegionPlugin.selectingObject = true);

        addLink.addActionListener(a -> addLink());
        submit.addActionListener(a -> submit());

        pack();
    }

    private void addLink() {
        listModel.addElement(new TransportLink(
                fromText.getText(),
                toText.getText(),
                actionText.getText(),
                objNameText.getText(),
                Integer.parseInt(objIdText.getText())
        ));

        fromText.setText(null);
        toText.setText(null);
        actionText.setText(null);
        objNameText.setText(null);
        objIdText.setText(null);
    }

    private void submit() {
        List<TransportLink> out = new ArrayList<>();
        while (listModel.elements().hasMoreElements()) {
            out.add(listModel.elements().nextElement());
        }

        EXECUTOR.schedule(() -> {
            try {
                String json = RegionManager.GSON.toJson(out);
                RequestBody body = RequestBody.create(RegionManager.JSON_MEDIATYPE, json);
                Request request = new Request.Builder()
                        .post(body)
                        .url(RegionManager.API_URL + "/transports")
                        .build();
                Response response = okHttpClient.newCall(request)
                        .execute();
                int code = response.code();
                if (code != 200) {
                    logger.error("Request was unsuccessful: {}", code);
                    return;
                }

                listModel.clear();
                logger.debug("Transports saved successfully");
            } catch (Exception e) {
                logger.error("Failed to POST: {}", e.getMessage());
                e.printStackTrace();
            }
        }, 5_000, TimeUnit.MILLISECONDS);
    }

    public void display() {
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Subscribe
    public void onMenuOptionClicked(MenuOptionClicked e) {
        RegionPlugin.selectingSourceTile = false;
        RegionPlugin.selectingDestinationTile = false;
        RegionPlugin.selectingObject = false;

        if (e.getId() == RegionPlugin.TileSelection.SOURCE.getId()) {
            e.consume();
            Tile hovered = Tiles.getHoveredTile();
            if (hovered == null) {
                return;
            }

            WorldPoint coord = hovered.getWorldLocation();

            fromText.setText(coord.getX() + " " + coord.getY() + " " + coord.getPlane());
            return;
        }

        if (e.getId() == RegionPlugin.TileSelection.DESTINATION.getId()) {
            e.consume();
            Tile hovered = Tiles.getHoveredTile();
            if (hovered == null) {
                return;
            }

            WorldPoint coord = hovered.getWorldLocation();

            toText.setText(coord.getX() + " " + coord.getY() + " " + coord.getPlane());
            return;
        }

        if (e.getId() == RegionPlugin.TileSelection.OBJECT.getId()) {
            e.consume();
            Tile hovered = Tiles.getHoveredTile();
            if (hovered == null) {
                return;
            }

            TileObject transport = TileObjects.getFirstAt(hovered, o -> o.getActions() != null
                    && o.getActions().stream().anyMatch(Objects::nonNull));
            if (transport == null) {
                return;
            }

            objNameText.setText(transport.getName());
            objIdText.setText(String.valueOf(transport.getId()));

            transport.getActions().stream().filter(Objects::nonNull).findFirst().ifPresent(actionText::setText);
        }
    }
}
