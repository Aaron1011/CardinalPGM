package in.twizmwaz.cardinal.module.modules.serveroffline;

import in.twizmwaz.cardinal.match.Match;
import in.twizmwaz.cardinal.module.ModuleBuilder;
import in.twizmwaz.cardinal.module.ModuleCollection;

public class ServerOfflineModuleBuilder implements ModuleBuilder {

    @Override
    public ModuleCollection load(Match match) {
        ModuleCollection<ServerOfflineModule> results = new ModuleCollection<ServerOfflineModule>();
        results.add(new ServerOfflineModule());
        return results;
    }
}
