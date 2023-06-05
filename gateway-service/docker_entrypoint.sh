if $DB_MIGRATION
then
  . ./db_migration.sh
else
 . ./run_application.sh
fi