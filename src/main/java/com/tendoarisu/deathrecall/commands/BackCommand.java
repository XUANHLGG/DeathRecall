package com.tendoarisu.deathrecall.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

/**
 * /back 命令的逻辑实现
 */
public class BackCommand {

    /**
     * 注册命令
     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("back")
                .requires(source -> source.hasPermission(0)) // 任何人都可以使用，无需 op
                .executes(context -> teleportToDeathLocation(context.getSource()))
        );
    }

    /**
     * 传送逻辑
     */
    private static int teleportToDeathLocation(CommandSourceStack source) {
        if (!(source.getEntity() instanceof ServerPlayer player)) {
            source.sendFailure(Component.literal("只有玩家可以执行此命令！"));
            return 0;
        }

        // 使用 vanilla 自带的最后死亡位置记录 (1.19+)
        Optional<GlobalPos> deathPosOpt = player.getLastDeathLocation();

        if (deathPosOpt.isEmpty()) {
            source.sendFailure(Component.literal("没有记录到你的死亡位置。"));
            return 0;
        }

        GlobalPos deathPos = deathPosOpt.get();
        ServerLevel targetLevel = player.getServer().getLevel(deathPos.dimension());
        
        if (targetLevel == null) {
            source.sendFailure(Component.literal("找不到死亡位置所在的维度。"));
            return 0;
        }

        // 传送玩家（支持跨维度）
        // 使用 Vec3 进行微调，确保玩家传送在方块中心
        Vec3 pos = new Vec3(deathPos.pos().getX() + 0.5, deathPos.pos().getY(), deathPos.pos().getZ() + 0.5);
        player.teleportTo(targetLevel, pos.x, pos.y, pos.z, player.getYRot(), player.getXRot());

        source.sendSuccess(() -> Component.literal("已回到上一次死亡地点！"), false);
        return 1;
    }
}
