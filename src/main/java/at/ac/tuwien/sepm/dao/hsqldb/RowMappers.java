package at.ac.tuwien.sepm.dao.hsqldb;


import at.ac.tuwien.sepm.entity.*;
import at.ac.tuwien.sepm.service.LvaType;
import at.ac.tuwien.sepm.service.Semester;
import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author Markus MUTH
 */
class RowMappers {
    static RowMapper<Curriculum> getCurriculumRowMapper() {
        return new RowMapper<Curriculum>() {
            @Override
            public Curriculum mapRow(ResultSet rs, int rowNum) throws SQLException {
                Curriculum entity = new Curriculum();
                entity.setId(rs.getInt(1));
                entity.setStudyNumber(rs.getString(2));
                entity.setName(rs.getString(3));
                entity.setDescription(rs.getString(4));
                entity.setAcademicTitle(rs.getString(5));
                entity.setEctsChoice(rs.getInt(6));
                entity.setEctsFree(rs.getInt(7));
                entity.setEctsSoftskill(rs.getInt(8));
                return entity;
            }
        };
    }

    static RowMapper<InCurriculum> getInCurriculumRowMapper() {
        return new RowMapper<InCurriculum>() {
            @Override
            public InCurriculum mapRow(ResultSet rs, int rowNum) throws SQLException {
                InCurriculum entity = new InCurriculum();
                entity.setCurriculum(rs.getInt(1));
                entity.setModule(rs.getInt(2));
                entity.setOlbigatory(rs.getBoolean(3));
                return entity;
            }
        };
    }

    static RowMapper<DateEntity> getDateRowMapper() {
        return new RowMapper<DateEntity>() {
            public DateEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                DateEntity entity = new DateEntity();
                entity.setId(rs.getInt(1));
                entity.setName(rs.getString(3));
                entity.setDescription(rs.getString(4));
                entity.setIntersectable(rs.getBoolean(5));
                entity.setStart(timestampToDateTimeConverter(rs.getTimestamp(6)));
                entity.setStop(timestampToDateTimeConverter(rs.getTimestamp(7)));
                return entity;
            }
        };
    }

    static DateTime timestampToDateTimeConverter (Timestamp t) {
        return new DateTime(t.getTime());
    }

    static RowMapper<LvaDate> getLvaDateRowMapper() {
        return new RowMapper<LvaDate>() {
            @Override
            public LvaDate mapRow(ResultSet rs, int rowNum) throws SQLException {
                LvaDate entity = new LvaDate();
                entity.setId(rs.getInt(1));
                //LVA lva = new LVA();
                //lva.setId(rs.getInt(2));
                entity.setLva(rs.getInt(2));
                entity.setName(rs.getString(3));
                entity.setDescription(rs.getString(4));
                entity.setType(LvaDateType.values()[rs.getInt(5)]);
                entity.setRoom(rs.getString(6));
                entity.setResult(rs.getInt(7));
                entity.setStart(timestampToDateTimeConverter(rs.getTimestamp(8)));
                entity.setStop(timestampToDateTimeConverter(rs.getTimestamp(9)));
                entity.setAttendanceRequired(rs.getBoolean(10));
                entity.setWasAttendant(rs.getBoolean(11));
                return entity;
            }
        };
    }

    static RowMapper<MetaLVA> getMetaLvaRowMapper() {
        return new RowMapper<MetaLVA>() {
            @Override
            public MetaLVA mapRow(ResultSet rs, int rowNum) throws SQLException {
                MetaLVA entity = new MetaLVA();
                entity.setId(rs.getInt(1));
                entity.setNr(rs.getString(2));
                entity.setName(rs.getString(3));
                entity.setSemestersOffered(Semester.values()[rs.getInt(4)]);
                entity.setType(LvaType.values()[rs.getInt(5)]);
                entity.setPriority(rs.getInt(6));
                entity.setECTS(rs.getFloat(7));
                entity.setModule(rs.getInt(8));
                return entity;
            }
        };
    }

    static RowMapper<Module> getModuleRowMapper() {
        return new RowMapper<Module>() {
            @Override
            public Module mapRow(ResultSet rs, int rowNum) throws SQLException {
                Module entity = new Module();
                entity.setId(rs.getInt(1));
                entity.setName(rs.getString(2));
                entity.setDescription(rs.getString(3));
                entity.setCompleteall(rs.getBoolean(4));
                return entity;
            }
        };
    }

    static RowMapper<Todo> getTodoRowMapper() {
        return new RowMapper<Todo>() {
            @Override
            public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
                Todo entity = new Todo();
                entity.setId(rs.getInt(1));

                LVA lva = new LVA();
                lva.setId(rs.getInt(2));
                entity.setLva(lva);

                entity.setName(rs.getString(3));
                entity.setDescirption(rs.getString(4));
                entity.setDone(rs.getBoolean(5));
                return entity;
            }
        };
    }

    static RowMapper<Track> getTrackRowMapper() {
        return new RowMapper<Track>() {
            @Override
            public Track mapRow(ResultSet rs, int rowNum) throws SQLException {
                Track entity = new Track();
                entity.setId(rs.getInt(1));

                LVA lva = new LVA();
                lva.setId(rs.getInt(2));
                entity.setLva(lva);

                entity.setName(rs.getString(3));
                entity.setDescription(rs.getString(4));
                entity.setStart(timestampToDateTimeConverter(rs.getTimestamp(5)));
                entity.setStop(timestampToDateTimeConverter(rs.getTimestamp(6)));
                return entity;
            }
        };
    }

    static RowMapper<LVA> getLvaRowMapper() {
        return new RowMapper<LVA>() {
            @Override
            public LVA mapRow(ResultSet rs, int rowNum) throws SQLException {
                LVA entity = new LVA();
                entity.setId(rs.getInt(1));
                MetaLVA metaLva = new MetaLVA();
                metaLva.setId(rs.getInt(2));
                entity.setMetaLVA(metaLva);
                entity.setYear(rs.getInt(3));
                boolean isWinterSemester = rs.getBoolean(4);
                if(isWinterSemester) {
                    entity.setSemester(Semester.W);
                } else {
                    entity.setSemester(Semester.S);
                }
                entity.setDescription(rs.getString((5)));
                entity.setGrade(rs.getInt(6));
                entity.setInStudyProgress(rs.getBoolean(7));
                return entity;
            }
        };
    }
    /*
    static RowMapper<LVA> getGeorgLvaRowMapper() {
        return new RowMapper<LVA>() {
            @Override
            public LVA mapRow(ResultSet rs, int rowNum) throws SQLException {
                LVA entity = new LVA();
                entity.setId(rs.getInt(1));
                MetaLVA metaLva = new MetaLVA();
                metaLva.setId(rs.getInt(2));
                entity.setMetaLVA(metaLva);
                entity.setYear(rs.getInt(3));
                boolean isWinterSemester = rs.getBoolean(4);
                if(isWinterSemester) {
                    entity.setSemester(Semester.W);
                } else {
                    entity.setSemester(Semester.S);
                }
                entity.setDescription(rs.getString(5));
                entity.setGrade(rs.getInt(6));
                entity.setInStudyProgress(rs.getBoolean(7));

                return entity;
            }
        };
    }
    */
    static RowMapper<Integer> getIntegerRowMapper() {
        return new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt(1);
            }
        };
    }
}