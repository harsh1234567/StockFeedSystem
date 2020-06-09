package com.filestockstorageservice.constants;

/**
 * The class Constant.
 * 
 * @author harsh.jain
 *
 */
public class Constant {

	/** THE Consants SYMBOL_NAME */
	public static final String SYMBOL_NAME = "symbolName";
	/** THE Consants LATEST_FEED */
	public static final String LATEST_FEED = "latestFeed";

	/** THE Consants SYMBOL_ID */
	public static final String SYMBOL_ID = "symbolId";
	/** THE Consants SPLIT */
	public static final String SPLIT = ",";
	/** The Constants FILES */
	public static final String FILES = "files";

	/** THE Consants COLON_COLON */
	public static final String COLON_COLON = "::";

	/** THE Consants REMOVE_QUOTES */
	public static final String REMOVE_QUOTES = "\"";

	/** The Constant UNPROCESSABLE_ENTITY. */
	public static final String UNPROCESSABLE_ENTITY = "Unprocessable Entity";

	/** THE Consants EMPTY */
	public static final String EMPTY = "";

	/** The Constant CONFLICT_REQUEST. */
	public static final String CONFLICT_REQUEST = "Conflict";
	/** The Constant BAD_REQUEST. */
	public static final String BAD_REQUEST = "Bad Request";

	/** The Constant OBJECT_NOT_FOUND_ERROR. */
	public static final String OBJECT_NOT_FOUND_ERROR = "Object not found";

	/** THE Consants INTERNAL_SERVER_ERROR */
	public static final String INTERNAL_SERVER_ERROR = "Internal server error ";

	/** THE Consants PROCESS_SPILT */
	public static final String PROCESS_SPILT = "\\r?\\n";

	/** THE Consants PROCESS_SPILT */
	public static final String TXT_EXCEPTION = "not implemented for text file!!";

	/** THE Consants PROCESS_SPILT */
	public static final String CSV_EXCEPTION = "csv not implemented";

	/** THE Consants CSV */
	public static final String CSV = "csv";

	/** THE Consants NOT_FOUND */
	public static final String NOT_FOUND = "404";

	/** THE Consants TXT */
	public static final String TXT = "txt";

	/** THE Consants JSON */
	public static final String JSON = "json";

	/** THE Consants FILE_NOT_FOUND */
	public static final String FILE_NOT_FOUND = "file not found";

	/** THE Consants COMMA */
	public static final String COMMA = ",";

	/** THE Consants COMMA_SPLIT */
	public static final String COMMA_SPLIT = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

	/** The Constants LIST_OF_SYMBOL */
	public static final String LIST_OF_SYMBOL = "listOfSymbols";

	/** The Constants INVALID_REQUEST */
	public static final String INVALID_REQUEST = "invalid parameter !";

	/** The Constants FILE_ERROR */
	public static final String FILE_ERROR = "NOT_FOUND_ERROR";

	/** The Constants INVALID_REQUEST_PARAM */
	public static final String INVALID_REQUEST_PARAM = "INVALID_REQUEST_PARAM_KEY";

	/** The Constants HEADER_REQUEST_ERROR */
	public static final String HEADER_REQUEST_ERROR = "HEADER_PARAM_NOT_PRESENT";

	/** The Constants ERROR_FILE_PROCESSING */
	public static final String ERROR_FILE_PROCESSING = "error occured while converting the file !!";

	/** The Constants FETCH_STOCK_DATA */
	public static final String FETCH_STOCK_DATA = "$$ -> flux Message -> %s\"";

	/** The Constants FILE_HEADER_NOT_FOUND */
	public static final String FILE_HEADER_NOT_FOUND = "file header not found";

	/** The Constants DATE_PATTERN */
	public static final String DATE_PATTERN = "yyyy-MM-dd hh:mm:ss";

	/** The Constants INVALID_FILE */
	public static final String INVALID_FILE = "invalid file found";

	/**
	 * Instantiates a new constants.
	 */
	private Constant() {
		super();
	}

}
