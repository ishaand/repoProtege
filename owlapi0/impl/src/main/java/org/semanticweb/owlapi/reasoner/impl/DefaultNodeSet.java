/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.semanticweb.owlapi.reasoner.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.util.CollectionFactory;

/**
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group, Date: 05-Dec-2009
 * @param <E>
 *        the type of owl objects in the node
 */
public abstract class DefaultNodeSet<E extends OWLObject> implements NodeSet<E> {

    private final Set<Node<E>> nodes = new HashSet<Node<E>>();

    /** constructor for an empty node set. */
    public DefaultNodeSet() {}

    /**
     * @param entity
     *        the entity to be contained
     */
    public DefaultNodeSet(E entity) {
        nodes.add(getNode(entity));
    }

    /**
     * @param node
     *        the node to be contained
     */
    public DefaultNodeSet(Node<E> node) {
        nodes.add(node);
    }

    /**
     * @param nodes
     *        a set of nodes to be contained
     */
    public DefaultNodeSet(Set<Node<E>> nodes) {
        this.nodes.addAll(nodes);
    }

    @Override
    public Set<Node<E>> getNodes() {
        return CollectionFactory
                .getCopyOnRequestSetFromMutableCollection(nodes);
    }

    /**
     * Adds an entity to this {@code NodeSet} by wrapping it in a {@code Node}.
     * 
     * @param entity
     *        The entity to be added. The entity will be wrapped in the
     *        {@code Node} and the {@code Node} added to this set. Must not be
     *        {@code null}.
     * @throws NullPointerException
     *         if {@code entity} is {@code null}.
     */
    public void addEntity(E entity) {
        if (entity == null) {
            throw new NullPointerException("entity cannot be null");
        }
        addNode(getNode(entity));
    }

    /**
     * Adds a {@code Node} to this set.
     * 
     * @param node
     *        The {@code Node} to be added.
     * @throws NullPointerException
     *         if {@code entity} is {@code null}.
     */
    public void addNode(Node<E> node) {
        if (node == null) {
            throw new NullPointerException("Cannot add null to a NodeSet");
        }
        nodes.add(node);
    }

    /**
     * Adds a collection of {@code Node}s to this set.
     * 
     * @param nodeset
     *        The {@code Node}s to be added. Note that if the collection is not
     *        a set then duplicate {@code Node}s will be filtered out.
     */
    public void addAllNodes(Collection<Node<E>> nodeset) {
        for (Node<E> node : nodeset) {
            if (node != null) {
                this.nodes.add(node);
            }
        }
    }

    /**
     * Adds the set of entities as a {@code Node} to this set.
     * 
     * @param entities
     *        The set of entities to be added. The entities will be wrapped in a
     *        {@code Node} which will be added to this {@code NodeSet}.
     */
    public void addSameEntities(Set<E> entities) {
        nodes.add(getNode(entities));
    }

    /**
     * Adds the specified entities as {@code Node}s to this set.
     * 
     * @param entities
     *        The entities to be added. Each entity will be wrapped in a
     *        {@code Node} which will then be added to this {@code NodeSet}.
     */
    public void addDifferentEntities(Set<E> entities) {
        for (E e : entities) {
            addNode(getNode(e));
        }
    }

    protected abstract DefaultNode<E> getNode(E entity);

    protected abstract DefaultNode<E> getNode(Set<E> entities);

    @Override
    public Set<E> getFlattened() {
        Set<E> result = new HashSet<E>();
        for (Node<E> node : nodes) {
            result.addAll(node.getEntities());
        }
        return result;
    }

    @Override
    public boolean isEmpty() {
        return nodes.isEmpty();
    }

    @Override
    public boolean containsEntity(E e) {
        for (Node<E> node : nodes) {
            if (node.contains(e)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isSingleton() {
        return nodes.size() == 1;
    }

    @Override
    public boolean isTopSingleton() {
        return isSingleton() && nodes.iterator().next().isTopNode();
    }

    @Override
    public boolean isBottomSingleton() {
        return isSingleton() && nodes.iterator().next().isBottomNode();
    }

    @Override
    public Iterator<Node<E>> iterator() {
        return nodes.iterator();
    }

    @Override
    public String toString() {
        return "Nodeset" + this.nodes.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof NodeSet)) {
            return false;
        }
        NodeSet<?> other = (NodeSet<?>) obj;
        return nodes.equals(other.getNodes());
    }

    @Override
    public int hashCode() {
        return nodes.hashCode();
    }
}
