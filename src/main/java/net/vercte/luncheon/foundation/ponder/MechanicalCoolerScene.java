package net.vercte.luncheon.foundation.ponder;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import com.simibubi.create.content.kinetics.press.MechanicalPressBlockEntity;
import com.simibubi.create.content.kinetics.press.PressingBehaviour;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.vercte.luncheon.registry.LuncheonItems;

public class MechanicalCoolerScene {
    public static void scene(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("mechanical_cooler", "Utilizing Mechanical Coolers");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();
        scene.idle(10);

        BlockPos cooler = util.grid().at(3, 1, 1);
        BlockPos burner = util.grid().at(1, 1, 3);
        scene.world().showSection(util.select().position(cooler), Direction.DOWN);
        scene.world().showSection(util.select().position(burner), Direction.DOWN);
        scene.idle(10);

        scene.overlay().showText(60)
                .attachKeyFrame()
                .text("The Mechanical Cooler is the Blaze Burner's cooler cousin")
                .pointAt(util.vector().blockSurface(cooler, Direction.WEST))
                .placeNearTarget();
        scene.idle(60);

        scene.world().hideSection(util.select().position(cooler), Direction.UP);
        scene.world().hideSection(util.select().position(burner), Direction.UP);
        scene.idle(10);

        BlockPos power = util.grid().at(2, 1, 2);
        Selection setup = util.select().fromTo(power, power.above(4));
        scene.world().showIndependentSection(setup, Direction.DOWN);
        scene.idle(20);

        Selection powerBehind = util.select().fromTo(util.grid().at(2, 1, 3), util.grid().at(2, 1, 5));
        BlockPos powerCog = util.grid().at(1, 0, 5);
        scene.world().showIndependentSection(powerBehind, Direction.NORTH);
        scene.world().showSection(util.select().position(powerCog), Direction.NORTH);
        scene.idle(20);

        scene.overlay().showText(60)
                .attachKeyFrame()
                .text("To operate, it needs power from below...")
                .pointAt(util.vector().blockSurface(power.above(), Direction.WEST))
                .placeNearTarget();
        scene.idle(40);

        Selection waterSetup = util.select().fromTo(util.grid().at(3, 1, 2), util.grid().at(4, 4, 4));
        scene.world().showIndependentSection(waterSetup, Direction.WEST);

        BlockPos pumpCogConnection = util.grid().at(2, 2, 3);
        BlockPos pump = util.grid().at(3,2,3);
        scene.world().setBlock(pumpCogConnection, AllBlocks.COGWHEEL.get()
                .defaultBlockState().setValue(RotatedPillarKineticBlock.AXIS, Direction.Axis.Z), false);
        scene.world().setKineticSpeed(util.select().position(pumpCogConnection), -64);
        scene.world().setKineticSpeed(util.select().position(pump), 64);
        scene.world().propagatePipeChange(pump);
        scene.world().showSection(util.select().position(pumpCogConnection), Direction.DOWN);

        scene.idle(40);

        scene.overlay().showText(60)
                .attachKeyFrame()
                .text("And water from the side.")
                .pointAt(util.vector().blockSurface(power.above(), Direction.EAST))
                .placeNearTarget();
        scene.idle(50);

        BlockPos examplePress = power.above(4);
        Class<MechanicalPressBlockEntity> pressType = MechanicalPressBlockEntity.class;
        scene.world().setKineticSpeed(util.select().position(examplePress), 64);

        Selection belt = util.select().fromTo(util.grid().at(1, 2, 2), util.grid().at(0, 1, 2));
        scene.world().showIndependentSection(belt, Direction.SOUTH);
        scene.world().modifyBlockEntity(examplePress, pressType, e -> e.getPressingBehaviour().start(PressingBehaviour.Mode.BASIN));

        ItemStack iceCube = new ItemStack(LuncheonItems.ICE_CUBE);
        BlockPos beltPos = util.grid().at(1, 2, 2);
        for(int i = 0; i < 2; i++) {
            scene.world().modifyBlockEntity(examplePress, pressType, e -> e.getPressingBehaviour().start(PressingBehaviour.Mode.BASIN));
            scene.idle(15);
            scene.world().createItemOnBelt(beltPos, Direction.SOUTH, iceCube);
            scene.idle(15);
        }
    }
}
