package com.feed_the_beast.ftbquests.integration.kubejs;

import com.feed_the_beast.ftbquests.FTBQuests;
import com.feed_the_beast.ftbquests.quest.ChangeProgress;
import com.feed_the_beast.ftbquests.quest.QuestData;
import com.feed_the_beast.ftbquests.quest.QuestFile;
import com.feed_the_beast.ftbquests.quest.QuestObjectBase;
import com.feed_the_beast.ftbquests.quest.QuestObjectType;
import com.feed_the_beast.ftbquests.quest.QuestShape;
import dev.latvian.kubejs.documentation.DocClass;
import dev.latvian.kubejs.documentation.DocField;
import dev.latvian.kubejs.documentation.DocMethod;
import dev.latvian.kubejs.documentation.Param;
import dev.latvian.kubejs.player.PlayerJS;
import dev.latvian.kubejs.world.WorldJS;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * @author LatvianModder
 */
@DocClass(displayName = "FTB Quests Integration")
public class FTBQuestsKubeJSWrapper
{
	@DocField
	public final Map<String, QuestShape> questShapes = QuestShape.NAME_MAP.map;

	@DocField
	public final Map<String, QuestObjectType> questObjectTypes = QuestObjectType.NAME_MAP.map;

	@DocField
	public final Map<String, ChangeProgress> changeProgressTypes = ChangeProgress.NAME_MAP.map;

	@DocMethod(value = "Currently loaded quest file. Can be null", params = @Param("world"))
	public QuestFile file(WorldJS world)
	{
		QuestFile f = FTBQuests.PROXY.getQuestFile(world.world);

		if (f == null)
		{
			throw new NullPointerException("Quest file isn't loaded!");
		}

		return f;
	}

	@Nullable
	@DocMethod(value = "Quest data from team UID", params = {@Param("world"), @Param(value = "team", type = short.class)})
	public QuestData data(WorldJS world, Number team)
	{
		return file(world).getData(team.shortValue());
	}

	@Nullable
	@DocMethod(value = "Quest data from team ID", params = {@Param("world"), @Param("team")})
	public QuestData data(WorldJS world, String team)
	{
		return file(world).getData(team);
	}

	@Nullable
	@DocMethod(value = "Quest data from player", params = @Param("player"))
	public QuestData data(PlayerJS player)
	{
		return file(player.world).getData(player.player);
	}

	@Nullable
	@DocMethod(value = "Quest object from object UID", params = {@Param("world"), @Param("id")})
	public QuestObjectBase object(WorldJS world, Object id)
	{
		if (id instanceof Number)
		{
			return file(world).getBase(((Number) id).intValue());
		}

		return object(world, QuestFile.getID(id.toString()));
	}
}