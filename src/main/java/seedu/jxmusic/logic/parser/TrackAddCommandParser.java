package seedu.jxmusic.logic.parser;

import static seedu.jxmusic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_PLAYLIST;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_TRACK;

import java.util.stream.Stream;

import seedu.jxmusic.logic.commands.TrackAddCommand;
import seedu.jxmusic.logic.parser.exceptions.ParseException;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.model.Track;

/**
 * Parses input arguments and creates a new PlaylistNewCommand object
 */
public class TrackAddCommandParser implements Parser<TrackAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PlaylistNewCommand
     * and returns an PlaylistNewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TrackAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PLAYLIST, PREFIX_TRACK);

        if (!arePrefixesPresent(argMultimap, PREFIX_PLAYLIST)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TrackAddCommand.MESSAGE_USAGE));
        }

        Playlist targetPlaylist = ParserUtil.parsePlaylist(argMultimap.getValue(PREFIX_PLAYLIST).get());
        Track trackToAdd = ParserUtil.parseTrack(argMultimap.getValue(PREFIX_TRACK).get());

        return new TrackAddCommand(trackToAdd, targetPlaylist);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
