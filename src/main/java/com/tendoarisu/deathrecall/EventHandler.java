package com.tendoarisu.deathrecall;

import com.tendoarisu.deathrecall.commands.BackCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 监听游戏内各种事件的处理器
 */
@Mod.EventBusSubscriber(modid = Deathrecall.MODID)
public class EventHandler {

    /**
     * 注册命令到服务器
     */
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        BackCommand.register(event.getDispatcher());
    }
}
