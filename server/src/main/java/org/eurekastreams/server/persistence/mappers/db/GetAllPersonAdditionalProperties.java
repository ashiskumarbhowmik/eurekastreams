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

import java.util.List;
import java.util.Map;

import org.eurekastreams.server.persistence.mappers.ReadMapper;

/**
 * This mapper just retrieves all People with accountId and additionalProperties populated from the db.
 * 
 */
public class GetAllPersonAdditionalProperties extends ReadMapper<Object, List<Map<String, Object>>>
{

    /**
     * Return all of the Person objects in the db with only accountId, and additionalProperties populated. 
     * {@inheritDoc}
     * .
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> execute(final Object inRequest)
    {
        return (List<Map<String, Object>>) getEntityManager().createQuery(
                "select new map(p.accountId as accountId, "
                        + "p.additionalProperties as additionalProperties) from Person p").getResultList();
    }

}
