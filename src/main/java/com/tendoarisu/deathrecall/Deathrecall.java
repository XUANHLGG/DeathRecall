package com.tendoarisu.deathrecall;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

/**
 * DeathRecall Mod 主类
 */
@Mod(Deathrecall.MODID)
public class Deathrecall {

    public static final String MODID = "deathrecall";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Deathrecall() {
        // 注册事件总线
        MinecraftForge.EVENT_BUS.register(this);
        
        LOGGER.info("DeathRecall Mod 已加载！");
    }
}
