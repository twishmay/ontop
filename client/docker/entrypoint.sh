#!/bin/bash

ONTOP_HOME=/opt/ontop

args_array=()

if [ "${MAPPING_FILE+x}" ]; then
  ONTOP_MAPPING_FILE=${MAPPING_FILE}
  echo "WARNING: environment variable MAPPING_FILE is deprecated. Please use ONTOP_MAPPING_FILE instead"
fi

if [ "${ONTOP_MAPPING_FILE+x}" ]; then
  args_array+=("--mapping=${ONTOP_MAPPING_FILE}")
else
  echo "ERROR: environment variable ONTOP_MAPPING_FILE is not set" && exit 1
fi

if [ "${ONTOLOGY_FILE+x}" ]; then
  ONTOP_ONTOLOGY_FILE=${ONTOLOGY_FILE}
  echo "WARNING: environment variable ONTOLOGY_FILE is deprecated. Please use ONTOP_ONTOLOGY_FILE instead"
fi

if [ "${ONTOP_ONTOLOGY_FILE+x}" ]; then
  args_array+=("--ontology=${ONTOP_ONTOLOGY_FILE}")
fi

if [ "${PROPERTIES_FILE+x}" ]; then
  ONTOP_PROPERTIES_FILE=${PROPERTIES_FILE}
  echo "WARNING: environment variable PROPERTIES_FILE is deprecated. Please use ONTOP_PROPERTIES_FILE instead"
fi

if [ "${ONTOP_PROPERTIES_FILE+x}" ]; then
  args_array+=("--properties=${ONTOP_PROPERTIES_FILE}")
else
  echo "ERROR: environment variable ONTOP_PROPERTIES_FILE is not set" && exit 1
fi

if [ "${ONTOP_DB_USER+x}" ]; then
  args_array+=("--db-user=${ONTOP_DB_USER}")
fi

if [ "${ONTOP_DB_USER_FILE+x}" ]; then
  if [ "${ONTOP_DB_USER+x}" ]; then
    echo "ERROR: environment variables ONTOP_DB_USER and ONTOP_DB_USER_FILE are conflicting. Please choose one of the two." && exit 1
  fi
  args_array+=("--db-user=$(< "${ONTOP_DB_USER_FILE}")")
fi

if [ "${ONTOP_DB_PASSWORD+x}" ]; then
  args_array+=("--db-password=${ONTOP_DB_PASSWORD}")
fi

if [ "${ONTOP_DB_PASSWORD_FILE+x}" ]; then
  if [ "${ONTOP_DB_PASSWORD+x}" ]; then
    echo "ERROR: environment variables ONTOP_DB_PASSWORD and ONTOP_DB_PASSWORD_FILE are conflicting. Please choose one of the two." && exit 1
  fi
  args_array+=("--db-password=$(< "${ONTOP_DB_PASSWORD_FILE}")")
fi

if [ "${ONTOP_DB_URL+x}" ]; then
  args_array+=("--db-url=${ONTOP_DB_URL}")
fi

if [ "${ONTOP_DB_URL_FILE+x}" ]; then
  if [ "${ONTOP_DB_URL+x}" ]; then
    echo "ERROR: environment variables ONTOP_DB_URL and ONTOP_DB_URL_FILE are conflicting. Please choose one of the two." && exit 1
  fi
  args_array+=("--db-url=$(< "${ONTOP_DB_URL_FILE}")")
fi

if [ "${ONTOP_XML_CATALOG_FILE+x}" ]; then
  args_array+=("--xml-catalog=${ONTOP_XML_CATALOG_FILE}")
fi

if [ "${ONTOP_CONSTRAINT_FILE+x}" ]; then
  args_array+=("--constraint=${ONTOP_CONSTRAINT_FILE}")
fi

if [ "${ONTOP_CORS_ALLOWED_ORIGINS+x}" ]; then
  args_array+=("--cors-allowed-origins=${ONTOP_CORS_ALLOWED_ORIGINS}")
fi

if [ "${ONTOP_PORTAL_FILE+x}" ]; then
  args_array+=("--portal=${ONTOP_PORTAL_FILE}")
fi

if [ "${ONTOP_PREDEFINED_CONFIG+x}" ]; then
  args_array+=("--predefined-config=${ONTOP_PREDEFINED_CONFIG}")
fi

if [ "${ONTOP_PREDEFINED_QUERIES+x}" ]; then
  args_array+=("--predefined-queries=${ONTOP_PREDEFINED_QUERIES}")
fi

if [ "${ONTOP_CONTEXTS+x}" ]; then
  args_array+=("--contexts=${ONTOP_CONTEXTS}")
fi

if [ "${ONTOP_DEV_MODE+x}" ]; then
  args_array+=("--dev")
fi

if [ "${ONTOP_LAZY_INIT+x}" ]; then
  args_array+=("--lazy")
fi

if [ -z "${ONTOP_JAVA_ARGS+x}" ]; then
  ONTOP_JAVA_ARGS="-Xmx512m"
fi

if [ "${ONTOP_DEBUG+x}" ]; then
  LOGBACK_CONFIG_FILE=${ONTOP_HOME}/log/logback-debug.xml
else
  LOGBACK_CONFIG_FILE=${ONTOP_HOME}/log/logback.xml
fi

java ${ONTOP_JAVA_ARGS} -cp "${ONTOP_HOME}/lib/*:${ONTOP_HOME}/jdbc/*" -Dlogging.config="${LOGBACK_CONFIG_FILE}" \
 it.unibz.inf.ontop.cli.Ontop endpoint "${args_array[@]}"
