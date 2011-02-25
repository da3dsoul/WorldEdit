// $Id$
/*
 * WorldEdit
 * Copyright (C) 2010, 2011 sk89q <http://www.sk89q.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
*/

package com.sk89q.worldedit.regions;

import java.util.ArrayList;
import java.util.List;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalPlayer;
import com.sk89q.worldedit.Vector;

/**
 * Selector for cuboids.
 *
 * @author sk89q
 */
public class CuboidRegionSelector implements RegionSelector {
    protected BlockVector pos1;
    protected BlockVector pos2;
    protected CuboidRegion region = new CuboidRegion(new Vector(), new Vector());

    public boolean selectPrimary(Vector pos) {
        if (pos1 != null && pos1.equals(pos)) {
            return false;
        }
        pos1 = pos.toBlockVector();
        region.setPos1(pos1);   
        return true;
    }

    public boolean selectSecondary(Vector pos) {
        if (pos2 != null && pos2.equals(pos)) {
            return false;
        }
        pos2 = pos.toBlockVector();
        region.setPos2(pos2);
        return true;
    }

    public void explainPrimarySelection(LocalPlayer player, Vector pos) {
        if (pos1 != null && pos2 != null) {
            player.print("First position set to " + pos1
                    + " (" + region.getArea() + ").");
        } else {
            player.print("First position set to " + pos1 + ".");
        }
    }

    public void explainSecondarySelection(LocalPlayer player, Vector pos) {
        if (pos1 != null && pos2 != null) {
            player.print("Second position set to " + pos2
                    + " (" + region.getArea() + ").");
        } else {
            player.print("Second position set to " + pos2 + ".");
        }
    }
    
    public BlockVector getPrimaryPosition() throws IncompleteRegionException {
        if (pos1 == null) {
            throw new IncompleteRegionException();
        }
        
        return pos1;
    }
    
    public boolean isDefined() {
        return pos1 != null && pos2 != null;
    }

    public Region getRegion() throws IncompleteRegionException {
        if (pos1 == null || pos2 == null) {
            throw new IncompleteRegionException();
        }
        
        return region;
    }

    public void learnChanges() {
        pos1 = region.getPos1().toBlockVector();
        pos2 = region.getPos2().toBlockVector();
    }
    
    public void clear() {
        pos1 = null;
        pos2 = null;
    }

    public String getTypeName() {
        return "cuboid";
    }

    public List<String> getInformationLines() {
        List<String> lines = new ArrayList<String>();
        
        if (pos1 != null) {
            lines.add("Position 1: " + pos1);
        }
        
        if (pos2 != null) {
            lines.add("Position 2: " + pos2);
        }
        
        return lines;
    }
}