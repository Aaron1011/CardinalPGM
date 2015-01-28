package in.twizmwaz.cardinal.module.modules.filter.type.logic;

import in.twizmwaz.cardinal.module.ModuleCollection;
import in.twizmwaz.cardinal.module.modules.filter.FilterModule;
import in.twizmwaz.cardinal.module.modules.filter.FilterState;

import static in.twizmwaz.cardinal.module.modules.filter.FilterState.ALLOW;
import static in.twizmwaz.cardinal.module.modules.filter.FilterState.DENY;

public class OneFilter extends FilterModule {

    private final ModuleCollection<FilterModule> children;

    public OneFilter(final String name, final ModuleCollection<FilterModule> children) {
        super(name);
        this.children = children;
    }

    @Override
    public FilterState evaluate(final Object object) {
        boolean found = false;
        for (FilterModule child : children) {
            if (child.evaluate(object).equals(ALLOW)) {
                if (!found) found = true;
                else return DENY;
            }
        }
        if (found) return ALLOW;
        else return DENY;
    }

}
