package in.twizmwaz.cardinal.module.modules.filter.type.old;

import in.twizmwaz.cardinal.module.ModuleCollection;
import in.twizmwaz.cardinal.module.modules.filter.FilterModule;
import in.twizmwaz.cardinal.module.modules.filter.FilterState;
import in.twizmwaz.cardinal.module.modules.filter.parsers.ChildrenFilterParser;
import in.twizmwaz.cardinal.module.modules.filter.parsers.GenericFilterParser;

import static in.twizmwaz.cardinal.module.modules.filter.FilterState.ABSTAIN;
import static in.twizmwaz.cardinal.module.modules.filter.FilterState.ALLOW;
import static in.twizmwaz.cardinal.module.modules.filter.FilterState.DENY;

public class AllowFilter extends FilterModule {

    private final ModuleCollection<FilterModule> children;

    public AllowFilter(final ChildrenFilterParser parser) {
        super(parser.getName());
        this.children = parser.getChildren();
    }

    @Override
    public FilterState evaluate(final Object... objects) {
        boolean abstain = true;
        for (FilterModule child : children) {
            if (child.evaluate(objects).equals(ALLOW)) return ALLOW;
            if (child.evaluate(objects).equals(ABSTAIN)) abstain = false;
        }
        if (abstain) return ABSTAIN;
        return DENY;
    }

}
