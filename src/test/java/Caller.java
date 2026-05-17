
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.title.Title;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.attribute.Attribute;
import net.minestom.server.entity.damage.Damage;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.block.Block;
import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.timer.TaskSchedule;
import org.customhud.api.HUDComponent;
import org.customhud.api.SpaceCreator;

import java.util.List;

public class Caller {
    public static void main() {
        HUDComponent backgroundImage = new HUDComponent("\uE003","test");
        HUDComponent batteryImage = new HUDComponent("\uE002","test");

        MinecraftServer mcServer = MinecraftServer.init();
        HUDComponent CoinImage = new HUDComponent("\uE001", "test");

        InstanceManager manager = MinecraftServer.getInstanceManager();
        InstanceContainer placeholder = manager.createInstanceContainer();

        placeholder.setGenerator(unit -> unit.modifier().fillHeight(0, 40, Block.GRASS_BLOCK));

        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(placeholder);
            player.setRespawnPoint(new Pos(0, 42, 0));
        });

        //test here
        globalEventHandler.addListener(PlayerSpawnEvent.class, event-> {
            Player player = event.getPlayer();

            player.scheduler().buildTask(() -> {
                //build health time
                float plrHealth = player.getHealth();
                float maxHP = (float) player.getAttribute(Attribute.MAX_HEALTH).getBaseValue();
                int numba = (int) Math.ceil((plrHealth / maxHP) *5);

                Component text = Component.join(JoinConfiguration.noSeparators(), backgroundImage.renderAndJoin());

                for (int i = 1; i <= 5 ; i++) {
                    if (i > numba) {
                        text = Component.join(JoinConfiguration.noSeparators(),text,
                                SpaceCreator.withSpace(8));
                        continue;
                    }

                    if (i == 1) {
                        //first
                        text = Component.join(JoinConfiguration.noSeparators(),text,
                                SpaceCreator.withSpace(-29), batteryImage.render());
                    } else {
                        text = Component.join(JoinConfiguration.noSeparators(),text,
                                SpaceCreator.withSpace(3),batteryImage.render());
                    }
                }
                player.sendActionBar(text);

                if (player.getAliveTicks() % 50 == 0) {
                    //damage
                    player.damage(Damage.fromPlayer(player,2f));
                }

            }).repeat(TaskSchedule.tick(1)).schedule();
        });

        mcServer.start("0.0.0.0",25565);
    }
}
