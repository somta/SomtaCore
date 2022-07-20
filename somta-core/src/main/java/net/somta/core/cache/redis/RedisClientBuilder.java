package net.somta.core.cache.redis;

import net.somta.core.cache.redis.client.AbstractRedisClient;
import net.somta.core.cache.redis.client.ClusterRedisClient;
import net.somta.core.cache.redis.client.SentinelRedisClient;
import net.somta.core.cache.redis.client.SingleRedisClient;
import net.somta.core.cache.redis.exception.RedisException;
import net.somta.core.cache.redis.model.RedisConfigItem;
import net.somta.core.cache.redis.model.RedisErrorEnum;
import net.somta.core.cache.redis.model.RedisModeEnum;
import net.somta.core.cache.redis.serialize.InterfaceSerializable;
import net.somta.core.cache.redis.serialize.JsonSerializable;

/**
 * @desc: Redis客户端构建类，构建不同类型的client
 * @author: husong
 * @date: 2022/7/13
 **/
public class RedisClientBuilder {

    /**
     * 构建Redis客户端
     * @param redisConfigItem
     * @return
     */
    public static AbstractRedisClient buildRedisClient(RedisConfigItem redisConfigItem){
        return buildRedisClient(redisConfigItem,null);
    }

    /**
     * 构建Redis客户端
     * @param redisConfigItem
     * @param interfaceSerializable
     * @return
     */
    public static AbstractRedisClient buildRedisClient(RedisConfigItem redisConfigItem,
                                                       InterfaceSerializable interfaceSerializable){
        if (interfaceSerializable == null) {
            interfaceSerializable = new JsonSerializable();
        }

        AbstractRedisClient redisClient = null;
        //根据类型实例化不同的类型
        if(RedisModeEnum.single.name().equals(redisConfigItem.getModel())){
            redisClient = new SingleRedisClient();
        }else if(RedisModeEnum.sentinel.name().equals(redisConfigItem.getModel())){
            redisClient = new SentinelRedisClient();
        }else if(RedisModeEnum.cluster.name().equals(redisConfigItem.getModel())){
            redisClient = new ClusterRedisClient();
        }else {
            throw new RedisException(RedisErrorEnum.REDIS_MODE_ERROR,redisConfigItem.getModel());
        }

        // init初始化配置
        redisClient.init(redisConfigItem);
        // 挂载序列化器
        redisClient.setInterfaceSerializable(interfaceSerializable);
        return redisClient;
    }
}