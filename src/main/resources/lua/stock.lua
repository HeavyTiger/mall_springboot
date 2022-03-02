local userId = ARGV[1];
local seckillId = ARGV[2];
local stockKey = "seckillStock:" .. seckillId;
local usersKey = "seckillOrder:" .. seckillId;
local userExists = redis.call("sIsMember", usersKey, userId);
if tonumber(userExists) == 1 then
    return 2;
end
local num = redis.call("get", stockKey);
if tonumber(num) <= 0 then
    return 0;
else
    redis.call("decr", stockKey);
    redis.call("sAdd", usersKey, userId);
end
return 1;