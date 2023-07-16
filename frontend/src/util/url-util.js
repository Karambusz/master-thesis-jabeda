import qs from 'qs';
import * as url from "url";

class UrlUtil {

    static constructUrlWithPathVariables = (path, pathVariables) => {
        if (!path || path.length === 0) {
            return null;
        }

        if (!pathVariables) {
            return path;
        }
        let result = path;

        for (const [key, value] of Object.entries(pathVariables)) {
            const pathVariablesValueRegex = new RegExp(`:${key}`, 'g');
            const uriEncodedValue = encodeURIComponent(value);
            result = result.replace(pathVariablesValueRegex, uriEncodedValue);
        }

        return result;
    }


    static constructUrl = ({path, pathVariables, queryObject}) => {
        const constructedPath = UrlUtil.constructUrlWithPathVariables(path, pathVariables);
        let queryParams = {};
        if (queryObject) {
                queryParams = {
                    search: qs.stringify(queryObject, {indices: false}),
                }
        }
        return url.format({
           pathname: constructedPath,
           ...queryParams
        });
    }
}

export default UrlUtil;