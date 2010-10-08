/*
 * Copyright (c) 2010 Lockheed Martin Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.eurekastreams.server.persistence.mappers.db;

import java.util.HashSet;
import java.util.Set;

import org.eurekastreams.server.persistence.mappers.BaseArgDomainMapper;

/**
 * Return list of direct child org ids for given org id.
 * 
 */
public class GetChildOrgIdsByOrgIdDbMapper extends BaseArgDomainMapper<Long, Set<Long>>
{
    /**
     * Return list of direct child org ids for given org id.
     * 
     * @param inRequest
     *            id.
     * @return list of direct child org ids for given org id.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Set<Long> execute(final Long inRequest)
    {
        return new HashSet<Long>(getEntityManager().createQuery(
                "SELECT id FROM Organization WHERE "
                        + "parentOrganization.id = :parentOrgId AND id != parentOrganization.id").setParameter(
                "parentOrgId", inRequest).getResultList());
    }

}