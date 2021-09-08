package com.github.zjjfly.concurrency.ch2;

import net.jcip.annotations.NotThreadSafe;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

/**
 * 另一个竞态条件的例子,读取-修改-写入,类似之前的UnsafeSequence
 *
 * @author zjjfly
 */
@NotThreadSafe
public class UnsafeCountingFactorizer extends HttpServlet {
    private long count = 0;

    public long getCount() {
        return count;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) {
        ++count;
    }

}
